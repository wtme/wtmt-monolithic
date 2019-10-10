// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { OvertimeHistoryComponentsPage, OvertimeHistoryDeleteDialog, OvertimeHistoryUpdatePage } from './overtime-history.page-object';

const expect = chai.expect;

describe('OvertimeHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let overtimeHistoryUpdatePage: OvertimeHistoryUpdatePage;
  let overtimeHistoryComponentsPage: OvertimeHistoryComponentsPage;
  let overtimeHistoryDeleteDialog: OvertimeHistoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OvertimeHistories', async () => {
    await navBarPage.goToEntity('overtime-history');
    overtimeHistoryComponentsPage = new OvertimeHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(overtimeHistoryComponentsPage.title), 5000);
    expect(await overtimeHistoryComponentsPage.getTitle()).to.eq('wmteApp.overtimeHistory.home.title');
  });

  it('should load create OvertimeHistory page', async () => {
    await overtimeHistoryComponentsPage.clickOnCreateButton();
    overtimeHistoryUpdatePage = new OvertimeHistoryUpdatePage();
    expect(await overtimeHistoryUpdatePage.getPageTitle()).to.eq('wmteApp.overtimeHistory.home.createOrEditLabel');
    await overtimeHistoryUpdatePage.cancel();
  });

  it('should create and save OvertimeHistories', async () => {
    const nbButtonsBeforeCreate = await overtimeHistoryComponentsPage.countDeleteButtons();

    await overtimeHistoryComponentsPage.clickOnCreateButton();
    await promise.all([
      overtimeHistoryUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overtimeHistoryUpdatePage.statusSelectLastOption(),
      overtimeHistoryUpdatePage.setChangeTypeInput('changeType'),
      overtimeHistoryUpdatePage.setWhoInput('who'),
      overtimeHistoryUpdatePage.overtimeSelectLastOption()
    ]);
    expect(await overtimeHistoryUpdatePage.getDateInput()).to.contain('2001-01-01T02:30', 'Expected date value to be equals to 2000-12-31');
    expect(await overtimeHistoryUpdatePage.getChangeTypeInput()).to.eq(
      'changeType',
      'Expected ChangeType value to be equals to changeType'
    );
    expect(await overtimeHistoryUpdatePage.getWhoInput()).to.eq('who', 'Expected Who value to be equals to who');
    await overtimeHistoryUpdatePage.save();
    expect(await overtimeHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await overtimeHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OvertimeHistory', async () => {
    const nbButtonsBeforeDelete = await overtimeHistoryComponentsPage.countDeleteButtons();
    await overtimeHistoryComponentsPage.clickOnLastDeleteButton();

    overtimeHistoryDeleteDialog = new OvertimeHistoryDeleteDialog();
    expect(await overtimeHistoryDeleteDialog.getDialogTitle()).to.eq('wmteApp.overtimeHistory.delete.question');
    await overtimeHistoryDeleteDialog.clickOnConfirmButton();

    expect(await overtimeHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
