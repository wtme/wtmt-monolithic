// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DepartmentComponentsPage, DepartmentDeleteDialog, DepartmentUpdatePage } from './department.page-object';

const expect = chai.expect;

describe('Department e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let departmentUpdatePage: DepartmentUpdatePage;
  let departmentComponentsPage: DepartmentComponentsPage;
  let departmentDeleteDialog: DepartmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Departments', async () => {
    await navBarPage.goToEntity('department');
    departmentComponentsPage = new DepartmentComponentsPage();
    await browser.wait(ec.visibilityOf(departmentComponentsPage.title), 5000);
    expect(await departmentComponentsPage.getTitle()).to.eq('wmteApp.department.home.title');
  });

  it('should load create Department page', async () => {
    await departmentComponentsPage.clickOnCreateButton();
    departmentUpdatePage = new DepartmentUpdatePage();
    expect(await departmentUpdatePage.getPageTitle()).to.eq('wmteApp.department.home.createOrEditLabel');
    await departmentUpdatePage.cancel();
  });

  it('should create and save Departments', async () => {
    const nbButtonsBeforeCreate = await departmentComponentsPage.countDeleteButtons();

    await departmentComponentsPage.clickOnCreateButton();
    await promise.all([
      departmentUpdatePage.setCodeInput('code'),
      departmentUpdatePage.setNameInput('name'),
      departmentUpdatePage.setDescriptionInput('description'),
      departmentUpdatePage.setNotesInput('notes'),
      departmentUpdatePage.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      departmentUpdatePage.setEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      departmentUpdatePage.locationSelectLastOption(),
      departmentUpdatePage.parentSelectLastOption()
    ]);
    expect(await departmentUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await departmentUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await departmentUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    const selectedDisabled = departmentUpdatePage.getDisabledInput();
    if (await selectedDisabled.isSelected()) {
      await departmentUpdatePage.getDisabledInput().click();
      expect(await departmentUpdatePage.getDisabledInput().isSelected(), 'Expected disabled not to be selected').to.be.false;
    } else {
      await departmentUpdatePage.getDisabledInput().click();
      expect(await departmentUpdatePage.getDisabledInput().isSelected(), 'Expected disabled to be selected').to.be.true;
    }
    expect(await departmentUpdatePage.getNotesInput()).to.eq('notes', 'Expected Notes value to be equals to notes');
    expect(await departmentUpdatePage.getStartDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected startDate value to be equals to 2000-12-31'
    );
    expect(await departmentUpdatePage.getEndDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected endDate value to be equals to 2000-12-31'
    );
    await departmentUpdatePage.save();
    expect(await departmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await departmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Department', async () => {
    const nbButtonsBeforeDelete = await departmentComponentsPage.countDeleteButtons();
    await departmentComponentsPage.clickOnLastDeleteButton();

    departmentDeleteDialog = new DepartmentDeleteDialog();
    expect(await departmentDeleteDialog.getDialogTitle()).to.eq('wmteApp.department.delete.question');
    await departmentDeleteDialog.clickOnConfirmButton();

    expect(await departmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
