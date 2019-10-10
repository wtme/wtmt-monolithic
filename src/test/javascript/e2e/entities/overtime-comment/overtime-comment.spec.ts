// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { OvertimeCommentComponentsPage, OvertimeCommentDeleteDialog, OvertimeCommentUpdatePage } from './overtime-comment.page-object';

const expect = chai.expect;

describe('OvertimeComment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let overtimeCommentUpdatePage: OvertimeCommentUpdatePage;
  let overtimeCommentComponentsPage: OvertimeCommentComponentsPage;
  let overtimeCommentDeleteDialog: OvertimeCommentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OvertimeComments', async () => {
    await navBarPage.goToEntity('overtime-comment');
    overtimeCommentComponentsPage = new OvertimeCommentComponentsPage();
    await browser.wait(ec.visibilityOf(overtimeCommentComponentsPage.title), 5000);
    expect(await overtimeCommentComponentsPage.getTitle()).to.eq('wmteApp.overtimeComment.home.title');
  });

  it('should load create OvertimeComment page', async () => {
    await overtimeCommentComponentsPage.clickOnCreateButton();
    overtimeCommentUpdatePage = new OvertimeCommentUpdatePage();
    expect(await overtimeCommentUpdatePage.getPageTitle()).to.eq('wmteApp.overtimeComment.home.createOrEditLabel');
    await overtimeCommentUpdatePage.cancel();
  });

  it('should create and save OvertimeComments', async () => {
    const nbButtonsBeforeCreate = await overtimeCommentComponentsPage.countDeleteButtons();

    await overtimeCommentComponentsPage.clickOnCreateButton();
    await promise.all([
      overtimeCommentUpdatePage.setCommentInput('comment'),
      overtimeCommentUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overtimeCommentUpdatePage.setEmployeeNameInput('employeeName'),
      overtimeCommentUpdatePage.setEmployeeEmailInput('employeeEmail'),
      overtimeCommentUpdatePage.overtimeSelectLastOption()
    ]);
    expect(await overtimeCommentUpdatePage.getCommentInput()).to.eq('comment', 'Expected Comment value to be equals to comment');
    expect(await overtimeCommentUpdatePage.getCreatedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createdDate value to be equals to 2000-12-31'
    );
    expect(await overtimeCommentUpdatePage.getEmployeeNameInput()).to.eq(
      'employeeName',
      'Expected EmployeeName value to be equals to employeeName'
    );
    expect(await overtimeCommentUpdatePage.getEmployeeEmailInput()).to.eq(
      'employeeEmail',
      'Expected EmployeeEmail value to be equals to employeeEmail'
    );
    await overtimeCommentUpdatePage.save();
    expect(await overtimeCommentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await overtimeCommentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OvertimeComment', async () => {
    const nbButtonsBeforeDelete = await overtimeCommentComponentsPage.countDeleteButtons();
    await overtimeCommentComponentsPage.clickOnLastDeleteButton();

    overtimeCommentDeleteDialog = new OvertimeCommentDeleteDialog();
    expect(await overtimeCommentDeleteDialog.getDialogTitle()).to.eq('wmteApp.overtimeComment.delete.question');
    await overtimeCommentDeleteDialog.clickOnConfirmButton();

    expect(await overtimeCommentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
