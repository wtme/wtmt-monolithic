// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DepartmentRoleComponentsPage, DepartmentRoleDeleteDialog, DepartmentRoleUpdatePage } from './department-role.page-object';

const expect = chai.expect;

describe('DepartmentRole e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let departmentRoleUpdatePage: DepartmentRoleUpdatePage;
  let departmentRoleComponentsPage: DepartmentRoleComponentsPage;
  let departmentRoleDeleteDialog: DepartmentRoleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DepartmentRoles', async () => {
    await navBarPage.goToEntity('department-role');
    departmentRoleComponentsPage = new DepartmentRoleComponentsPage();
    await browser.wait(ec.visibilityOf(departmentRoleComponentsPage.title), 5000);
    expect(await departmentRoleComponentsPage.getTitle()).to.eq('wmteApp.departmentRole.home.title');
  });

  it('should load create DepartmentRole page', async () => {
    await departmentRoleComponentsPage.clickOnCreateButton();
    departmentRoleUpdatePage = new DepartmentRoleUpdatePage();
    expect(await departmentRoleUpdatePage.getPageTitle()).to.eq('wmteApp.departmentRole.home.createOrEditLabel');
    await departmentRoleUpdatePage.cancel();
  });

  it('should create and save DepartmentRoles', async () => {
    const nbButtonsBeforeCreate = await departmentRoleComponentsPage.countDeleteButtons();

    await departmentRoleComponentsPage.clickOnCreateButton();
    await promise.all([
      departmentRoleUpdatePage.setCodeInput('code'),
      departmentRoleUpdatePage.setNameInput('name'),
      departmentRoleUpdatePage.setDescriptionInput('description')
    ]);
    expect(await departmentRoleUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await departmentRoleUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await departmentRoleUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    await departmentRoleUpdatePage.save();
    expect(await departmentRoleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await departmentRoleComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DepartmentRole', async () => {
    const nbButtonsBeforeDelete = await departmentRoleComponentsPage.countDeleteButtons();
    await departmentRoleComponentsPage.clickOnLastDeleteButton();

    departmentRoleDeleteDialog = new DepartmentRoleDeleteDialog();
    expect(await departmentRoleDeleteDialog.getDialogTitle()).to.eq('wmteApp.departmentRole.delete.question');
    await departmentRoleDeleteDialog.clickOnConfirmButton();

    expect(await departmentRoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
