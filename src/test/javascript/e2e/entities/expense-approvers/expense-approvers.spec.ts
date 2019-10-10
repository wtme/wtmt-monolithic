// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ExpenseApproversComponentsPage, ExpenseApproversDeleteDialog, ExpenseApproversUpdatePage } from './expense-approvers.page-object';

const expect = chai.expect;

describe('ExpenseApprovers e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let expenseApproversUpdatePage: ExpenseApproversUpdatePage;
  let expenseApproversComponentsPage: ExpenseApproversComponentsPage;
  let expenseApproversDeleteDialog: ExpenseApproversDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ExpenseApprovers', async () => {
    await navBarPage.goToEntity('expense-approvers');
    expenseApproversComponentsPage = new ExpenseApproversComponentsPage();
    await browser.wait(ec.visibilityOf(expenseApproversComponentsPage.title), 5000);
    expect(await expenseApproversComponentsPage.getTitle()).to.eq('wmteApp.expenseApprovers.home.title');
  });

  it('should load create ExpenseApprovers page', async () => {
    await expenseApproversComponentsPage.clickOnCreateButton();
    expenseApproversUpdatePage = new ExpenseApproversUpdatePage();
    expect(await expenseApproversUpdatePage.getPageTitle()).to.eq('wmteApp.expenseApprovers.home.createOrEditLabel');
    await expenseApproversUpdatePage.cancel();
  });

  it('should create and save ExpenseApprovers', async () => {
    const nbButtonsBeforeCreate = await expenseApproversComponentsPage.countDeleteButtons();

    await expenseApproversComponentsPage.clickOnCreateButton();
    await promise.all([
      expenseApproversUpdatePage.employeeSelectLastOption(),
      expenseApproversUpdatePage.departmentSelectLastOption(),
      expenseApproversUpdatePage.departmentRoleSelectLastOption()
    ]);
    await expenseApproversUpdatePage.save();
    expect(await expenseApproversUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await expenseApproversComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ExpenseApprovers', async () => {
    const nbButtonsBeforeDelete = await expenseApproversComponentsPage.countDeleteButtons();
    await expenseApproversComponentsPage.clickOnLastDeleteButton();

    expenseApproversDeleteDialog = new ExpenseApproversDeleteDialog();
    expect(await expenseApproversDeleteDialog.getDialogTitle()).to.eq('wmteApp.expenseApprovers.delete.question');
    await expenseApproversDeleteDialog.clickOnConfirmButton();

    expect(await expenseApproversComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
