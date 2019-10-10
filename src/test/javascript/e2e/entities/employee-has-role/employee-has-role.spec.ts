// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { EmployeeHasRoleComponentsPage, EmployeeHasRoleDeleteDialog, EmployeeHasRoleUpdatePage } from './employee-has-role.page-object';

const expect = chai.expect;

describe('EmployeeHasRole e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeHasRoleUpdatePage: EmployeeHasRoleUpdatePage;
  let employeeHasRoleComponentsPage: EmployeeHasRoleComponentsPage;
  let employeeHasRoleDeleteDialog: EmployeeHasRoleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmployeeHasRoles', async () => {
    await navBarPage.goToEntity('employee-has-role');
    employeeHasRoleComponentsPage = new EmployeeHasRoleComponentsPage();
    await browser.wait(ec.visibilityOf(employeeHasRoleComponentsPage.title), 5000);
    expect(await employeeHasRoleComponentsPage.getTitle()).to.eq('wmteApp.employeeHasRole.home.title');
  });

  it('should load create EmployeeHasRole page', async () => {
    await employeeHasRoleComponentsPage.clickOnCreateButton();
    employeeHasRoleUpdatePage = new EmployeeHasRoleUpdatePage();
    expect(await employeeHasRoleUpdatePage.getPageTitle()).to.eq('wmteApp.employeeHasRole.home.createOrEditLabel');
    await employeeHasRoleUpdatePage.cancel();
  });

  it('should create and save EmployeeHasRoles', async () => {
    const nbButtonsBeforeCreate = await employeeHasRoleComponentsPage.countDeleteButtons();

    await employeeHasRoleComponentsPage.clickOnCreateButton();
    await promise.all([employeeHasRoleUpdatePage.setNameInput('name'), employeeHasRoleUpdatePage.employeeSelectLastOption()]);
    expect(await employeeHasRoleUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await employeeHasRoleUpdatePage.save();
    expect(await employeeHasRoleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeHasRoleComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmployeeHasRole', async () => {
    const nbButtonsBeforeDelete = await employeeHasRoleComponentsPage.countDeleteButtons();
    await employeeHasRoleComponentsPage.clickOnLastDeleteButton();

    employeeHasRoleDeleteDialog = new EmployeeHasRoleDeleteDialog();
    expect(await employeeHasRoleDeleteDialog.getDialogTitle()).to.eq('wmteApp.employeeHasRole.delete.question');
    await employeeHasRoleDeleteDialog.clickOnConfirmButton();

    expect(await employeeHasRoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
