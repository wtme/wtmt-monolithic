import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee div table .btn-danger'));
  title = element.all(by.css('jhi-employee div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class EmployeeUpdatePage {
  pageTitle = element(by.id('jhi-employee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  fullnameInput = element(by.id('field_fullname'));
  loginInput = element(by.id('field_login'));
  emailInput = element(by.id('field_email'));
  personalEmailInput = element(by.id('field_personalEmail'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  mobileInput = element(by.id('field_mobile'));
  dateOfJoiningInput = element(by.id('field_dateOfJoining'));
  genderSelect = element(by.id('field_gender'));
  statusSelect = element(by.id('field_status'));
  employeeNumberInput = element(by.id('field_employeeNumber'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  noteInput = element(by.id('field_note'));
  userIdInput = element(by.id('field_userId'));
  managerSelect = element(by.id('field_manager'));
  departmenSelect = element(by.id('field_departmen'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setFullnameInput(fullname) {
    await this.fullnameInput.sendKeys(fullname);
  }

  async getFullnameInput() {
    return await this.fullnameInput.getAttribute('value');
  }

  async setLoginInput(login) {
    await this.loginInput.sendKeys(login);
  }

  async getLoginInput() {
    return await this.loginInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setPersonalEmailInput(personalEmail) {
    await this.personalEmailInput.sendKeys(personalEmail);
  }

  async getPersonalEmailInput() {
    return await this.personalEmailInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setMobileInput(mobile) {
    await this.mobileInput.sendKeys(mobile);
  }

  async getMobileInput() {
    return await this.mobileInput.getAttribute('value');
  }

  async setDateOfJoiningInput(dateOfJoining) {
    await this.dateOfJoiningInput.sendKeys(dateOfJoining);
  }

  async getDateOfJoiningInput() {
    return await this.dateOfJoiningInput.getAttribute('value');
  }

  async setGenderSelect(gender) {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect() {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(timeout?: number) {
    await this.genderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(timeout?: number) {
    await this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setEmployeeNumberInput(employeeNumber) {
    await this.employeeNumberInput.sendKeys(employeeNumber);
  }

  async getEmployeeNumberInput() {
    return await this.employeeNumberInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth) {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput() {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return await this.noteInput.getAttribute('value');
  }

  async setUserIdInput(userId) {
    await this.userIdInput.sendKeys(userId);
  }

  async getUserIdInput() {
    return await this.userIdInput.getAttribute('value');
  }

  async managerSelectLastOption(timeout?: number) {
    await this.managerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async managerSelectOption(option) {
    await this.managerSelect.sendKeys(option);
  }

  getManagerSelect(): ElementFinder {
    return this.managerSelect;
  }

  async getManagerSelectedOption() {
    return await this.managerSelect.element(by.css('option:checked')).getText();
  }

  async departmenSelectLastOption(timeout?: number) {
    await this.departmenSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async departmenSelectOption(option) {
    await this.departmenSelect.sendKeys(option);
  }

  getDepartmenSelect(): ElementFinder {
    return this.departmenSelect;
  }

  async getDepartmenSelectedOption() {
    return await this.departmenSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EmployeeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employee-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employee'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
