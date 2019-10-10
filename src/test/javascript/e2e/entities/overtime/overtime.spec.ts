// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { OvertimeComponentsPage, OvertimeDeleteDialog, OvertimeUpdatePage } from './overtime.page-object';

const expect = chai.expect;

describe('Overtime e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let overtimeUpdatePage: OvertimeUpdatePage;
  let overtimeComponentsPage: OvertimeComponentsPage;
  let overtimeDeleteDialog: OvertimeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Overtimes', async () => {
    await navBarPage.goToEntity('overtime');
    overtimeComponentsPage = new OvertimeComponentsPage();
    await browser.wait(ec.visibilityOf(overtimeComponentsPage.title), 5000);
    expect(await overtimeComponentsPage.getTitle()).to.eq('wmteApp.overtime.home.title');
  });

  it('should load create Overtime page', async () => {
    await overtimeComponentsPage.clickOnCreateButton();
    overtimeUpdatePage = new OvertimeUpdatePage();
    expect(await overtimeUpdatePage.getPageTitle()).to.eq('wmteApp.overtime.home.createOrEditLabel');
    await overtimeUpdatePage.cancel();
  });

  it('should create and save Overtimes', async () => {
    const nbButtonsBeforeCreate = await overtimeComponentsPage.countDeleteButtons();

    await overtimeComponentsPage.clickOnCreateButton();
    await promise.all([
      overtimeUpdatePage.setCodeInput('code'),
      overtimeUpdatePage.setNameInput('name'),
      overtimeUpdatePage.statusSelectLastOption(),
      overtimeUpdatePage.setDetailsInput('details'),
      overtimeUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overtimeUpdatePage.setToDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overtimeUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overtimeUpdatePage.setOvertimeInHoursInput('5'),
      overtimeUpdatePage.setTotalBillableHoursInput('5'),
      overtimeUpdatePage.setTotalCostingAmountInput('5'),
      overtimeUpdatePage.setNoteInput('note'),
      overtimeUpdatePage.setLocationInput('location'),
      overtimeUpdatePage.setEmployeeNameInput('employeeName'),
      overtimeUpdatePage.setEmployeeEmailInput('employeeEmail'),
      overtimeUpdatePage.setDepartmentNameInput('departmentName'),
      overtimeUpdatePage.setDepartmentCodeInput('departmentCode')
    ]);
    expect(await overtimeUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await overtimeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await overtimeUpdatePage.getDetailsInput()).to.eq('details', 'Expected Details value to be equals to details');
    expect(await overtimeUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await overtimeUpdatePage.getToDateInput()).to.contain('2001-01-01T02:30', 'Expected toDate value to be equals to 2000-12-31');
    expect(await overtimeUpdatePage.getCreatedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createdDate value to be equals to 2000-12-31'
    );
    expect(await overtimeUpdatePage.getOvertimeInHoursInput()).to.eq('5', 'Expected overtimeInHours value to be equals to 5');
    expect(await overtimeUpdatePage.getTotalBillableHoursInput()).to.eq('5', 'Expected totalBillableHours value to be equals to 5');
    expect(await overtimeUpdatePage.getTotalCostingAmountInput()).to.eq('5', 'Expected totalCostingAmount value to be equals to 5');
    expect(await overtimeUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');
    expect(await overtimeUpdatePage.getLocationInput()).to.eq('location', 'Expected Location value to be equals to location');
    expect(await overtimeUpdatePage.getEmployeeNameInput()).to.eq(
      'employeeName',
      'Expected EmployeeName value to be equals to employeeName'
    );
    expect(await overtimeUpdatePage.getEmployeeEmailInput()).to.eq(
      'employeeEmail',
      'Expected EmployeeEmail value to be equals to employeeEmail'
    );
    expect(await overtimeUpdatePage.getDepartmentNameInput()).to.eq(
      'departmentName',
      'Expected DepartmentName value to be equals to departmentName'
    );
    expect(await overtimeUpdatePage.getDepartmentCodeInput()).to.eq(
      'departmentCode',
      'Expected DepartmentCode value to be equals to departmentCode'
    );
    await overtimeUpdatePage.save();
    expect(await overtimeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await overtimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Overtime', async () => {
    const nbButtonsBeforeDelete = await overtimeComponentsPage.countDeleteButtons();
    await overtimeComponentsPage.clickOnLastDeleteButton();

    overtimeDeleteDialog = new OvertimeDeleteDialog();
    expect(await overtimeDeleteDialog.getDialogTitle()).to.eq('wmteApp.overtime.delete.question');
    await overtimeDeleteDialog.clickOnConfirmButton();

    expect(await overtimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
