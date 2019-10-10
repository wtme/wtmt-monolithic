// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { EmployeeComponentsPage, EmployeeDeleteDialog, EmployeeUpdatePage } from './employee.page-object';

const expect = chai.expect;

describe('Employee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeUpdatePage: EmployeeUpdatePage;
  let employeeComponentsPage: EmployeeComponentsPage;
  let employeeDeleteDialog: EmployeeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Employees', async () => {
    await navBarPage.goToEntity('employee');
    employeeComponentsPage = new EmployeeComponentsPage();
    await browser.wait(ec.visibilityOf(employeeComponentsPage.title), 5000);
    expect(await employeeComponentsPage.getTitle()).to.eq('wmteApp.employee.home.title');
  });

  it('should load create Employee page', async () => {
    await employeeComponentsPage.clickOnCreateButton();
    employeeUpdatePage = new EmployeeUpdatePage();
    expect(await employeeUpdatePage.getPageTitle()).to.eq('wmteApp.employee.home.createOrEditLabel');
    await employeeUpdatePage.cancel();
  });

  it('should create and save Employees', async () => {
    const nbButtonsBeforeCreate = await employeeComponentsPage.countDeleteButtons();

    await employeeComponentsPage.clickOnCreateButton();
    await promise.all([
      employeeUpdatePage.setCodeInput('code'),
      employeeUpdatePage.setFullnameInput('fullname'),
      employeeUpdatePage.setLoginInput('login'),
      employeeUpdatePage.setEmailInput('email'),
      employeeUpdatePage.setPersonalEmailInput('personalEmail'),
      employeeUpdatePage.setPhoneNumberInput('phoneNumber'),
      employeeUpdatePage.setMobileInput('mobile'),
      employeeUpdatePage.setDateOfJoiningInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeeUpdatePage.genderSelectLastOption(),
      employeeUpdatePage.statusSelectLastOption(),
      employeeUpdatePage.setEmployeeNumberInput('employeeNumber'),
      employeeUpdatePage.setDateOfBirthInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeeUpdatePage.setNoteInput('note'),
      employeeUpdatePage.setUserIdInput('userId'),
      employeeUpdatePage.managerSelectLastOption()
    ]);
    expect(await employeeUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await employeeUpdatePage.getFullnameInput()).to.eq('fullname', 'Expected Fullname value to be equals to fullname');
    expect(await employeeUpdatePage.getLoginInput()).to.eq('login', 'Expected Login value to be equals to login');
    expect(await employeeUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await employeeUpdatePage.getPersonalEmailInput()).to.eq(
      'personalEmail',
      'Expected PersonalEmail value to be equals to personalEmail'
    );
    expect(await employeeUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber', 'Expected PhoneNumber value to be equals to phoneNumber');
    expect(await employeeUpdatePage.getMobileInput()).to.eq('mobile', 'Expected Mobile value to be equals to mobile');
    expect(await employeeUpdatePage.getDateOfJoiningInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfJoining value to be equals to 2000-12-31'
    );
    expect(await employeeUpdatePage.getEmployeeNumberInput()).to.eq(
      'employeeNumber',
      'Expected EmployeeNumber value to be equals to employeeNumber'
    );
    expect(await employeeUpdatePage.getDateOfBirthInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfBirth value to be equals to 2000-12-31'
    );
    expect(await employeeUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');
    expect(await employeeUpdatePage.getUserIdInput()).to.eq('userId', 'Expected UserId value to be equals to userId');
    await employeeUpdatePage.save();
    expect(await employeeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employee', async () => {
    const nbButtonsBeforeDelete = await employeeComponentsPage.countDeleteButtons();
    await employeeComponentsPage.clickOnLastDeleteButton();

    employeeDeleteDialog = new EmployeeDeleteDialog();
    expect(await employeeDeleteDialog.getDialogTitle()).to.eq('wmteApp.employee.delete.question');
    await employeeDeleteDialog.clickOnConfirmButton();

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
