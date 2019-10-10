// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  EmployeeDepartmentComponentsPage,
  EmployeeDepartmentDeleteDialog,
  EmployeeDepartmentUpdatePage
} from './employee-department.page-object';

const expect = chai.expect;

describe('EmployeeDepartment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeDepartmentUpdatePage: EmployeeDepartmentUpdatePage;
  let employeeDepartmentComponentsPage: EmployeeDepartmentComponentsPage;
  let employeeDepartmentDeleteDialog: EmployeeDepartmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmployeeDepartments', async () => {
    await navBarPage.goToEntity('employee-department');
    employeeDepartmentComponentsPage = new EmployeeDepartmentComponentsPage();
    await browser.wait(ec.visibilityOf(employeeDepartmentComponentsPage.title), 5000);
    expect(await employeeDepartmentComponentsPage.getTitle()).to.eq('wmteApp.employeeDepartment.home.title');
  });

  it('should load create EmployeeDepartment page', async () => {
    await employeeDepartmentComponentsPage.clickOnCreateButton();
    employeeDepartmentUpdatePage = new EmployeeDepartmentUpdatePage();
    expect(await employeeDepartmentUpdatePage.getPageTitle()).to.eq('wmteApp.employeeDepartment.home.createOrEditLabel');
    await employeeDepartmentUpdatePage.cancel();
  });

  it('should create and save EmployeeDepartments', async () => {
    const nbButtonsBeforeCreate = await employeeDepartmentComponentsPage.countDeleteButtons();

    await employeeDepartmentComponentsPage.clickOnCreateButton();
    await promise.all([employeeDepartmentUpdatePage.employeeSelectLastOption(), employeeDepartmentUpdatePage.departmentSelectLastOption()]);
    await employeeDepartmentUpdatePage.save();
    expect(await employeeDepartmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeDepartmentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmployeeDepartment', async () => {
    const nbButtonsBeforeDelete = await employeeDepartmentComponentsPage.countDeleteButtons();
    await employeeDepartmentComponentsPage.clickOnLastDeleteButton();

    employeeDepartmentDeleteDialog = new EmployeeDepartmentDeleteDialog();
    expect(await employeeDepartmentDeleteDialog.getDialogTitle()).to.eq('wmteApp.employeeDepartment.delete.question');
    await employeeDepartmentDeleteDialog.clickOnConfirmButton();

    expect(await employeeDepartmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
