// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { OvertimeApprovalComponentsPage, OvertimeApprovalDeleteDialog, OvertimeApprovalUpdatePage } from './overtime-approval.page-object';

const expect = chai.expect;

describe('OvertimeApproval e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let overtimeApprovalUpdatePage: OvertimeApprovalUpdatePage;
  let overtimeApprovalComponentsPage: OvertimeApprovalComponentsPage;
  let overtimeApprovalDeleteDialog: OvertimeApprovalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OvertimeApprovals', async () => {
    await navBarPage.goToEntity('overtime-approval');
    overtimeApprovalComponentsPage = new OvertimeApprovalComponentsPage();
    await browser.wait(ec.visibilityOf(overtimeApprovalComponentsPage.title), 5000);
    expect(await overtimeApprovalComponentsPage.getTitle()).to.eq('wmteApp.overtimeApproval.home.title');
  });

  it('should load create OvertimeApproval page', async () => {
    await overtimeApprovalComponentsPage.clickOnCreateButton();
    overtimeApprovalUpdatePage = new OvertimeApprovalUpdatePage();
    expect(await overtimeApprovalUpdatePage.getPageTitle()).to.eq('wmteApp.overtimeApproval.home.createOrEditLabel');
    await overtimeApprovalUpdatePage.cancel();
  });

  it('should create and save OvertimeApprovals', async () => {
    const nbButtonsBeforeCreate = await overtimeApprovalComponentsPage.countDeleteButtons();

    await overtimeApprovalComponentsPage.clickOnCreateButton();
    await promise.all([
      overtimeApprovalUpdatePage.setApproverNameInput('approverName'),
      overtimeApprovalUpdatePage.setApproverEmailInput('approverEmail'),
      overtimeApprovalUpdatePage.overtimeSelectLastOption()
    ]);
    expect(await overtimeApprovalUpdatePage.getApproverNameInput()).to.eq(
      'approverName',
      'Expected ApproverName value to be equals to approverName'
    );
    expect(await overtimeApprovalUpdatePage.getApproverEmailInput()).to.eq(
      'approverEmail',
      'Expected ApproverEmail value to be equals to approverEmail'
    );
    await overtimeApprovalUpdatePage.save();
    expect(await overtimeApprovalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await overtimeApprovalComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OvertimeApproval', async () => {
    const nbButtonsBeforeDelete = await overtimeApprovalComponentsPage.countDeleteButtons();
    await overtimeApprovalComponentsPage.clickOnLastDeleteButton();

    overtimeApprovalDeleteDialog = new OvertimeApprovalDeleteDialog();
    expect(await overtimeApprovalDeleteDialog.getDialogTitle()).to.eq('wmteApp.overtimeApproval.delete.question');
    await overtimeApprovalDeleteDialog.clickOnConfirmButton();

    expect(await overtimeApprovalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
