// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  DepartmentApproveComponentsPage,
  DepartmentApproveDeleteDialog,
  DepartmentApproveUpdatePage
} from './department-approve.page-object';

const expect = chai.expect;

describe('DepartmentApprove e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let departmentApproveUpdatePage: DepartmentApproveUpdatePage;
  let departmentApproveComponentsPage: DepartmentApproveComponentsPage;
  let departmentApproveDeleteDialog: DepartmentApproveDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DepartmentApproves', async () => {
    await navBarPage.goToEntity('department-approve');
    departmentApproveComponentsPage = new DepartmentApproveComponentsPage();
    await browser.wait(ec.visibilityOf(departmentApproveComponentsPage.title), 5000);
    expect(await departmentApproveComponentsPage.getTitle()).to.eq('wmteApp.departmentApprove.home.title');
  });

  it('should load create DepartmentApprove page', async () => {
    await departmentApproveComponentsPage.clickOnCreateButton();
    departmentApproveUpdatePage = new DepartmentApproveUpdatePage();
    expect(await departmentApproveUpdatePage.getPageTitle()).to.eq('wmteApp.departmentApprove.home.createOrEditLabel');
    await departmentApproveUpdatePage.cancel();
  });

  it('should create and save DepartmentApproves', async () => {
    const nbButtonsBeforeCreate = await departmentApproveComponentsPage.countDeleteButtons();

    await departmentApproveComponentsPage.clickOnCreateButton();
    await promise.all([
      departmentApproveUpdatePage.employeeSelectLastOption(),
      departmentApproveUpdatePage.departmentSelectLastOption(),
      departmentApproveUpdatePage.departmentRoleSelectLastOption()
    ]);
    await departmentApproveUpdatePage.save();
    expect(await departmentApproveUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await departmentApproveComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DepartmentApprove', async () => {
    const nbButtonsBeforeDelete = await departmentApproveComponentsPage.countDeleteButtons();
    await departmentApproveComponentsPage.clickOnLastDeleteButton();

    departmentApproveDeleteDialog = new DepartmentApproveDeleteDialog();
    expect(await departmentApproveDeleteDialog.getDialogTitle()).to.eq('wmteApp.departmentApprove.delete.question');
    await departmentApproveDeleteDialog.clickOnConfirmButton();

    expect(await departmentApproveComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
