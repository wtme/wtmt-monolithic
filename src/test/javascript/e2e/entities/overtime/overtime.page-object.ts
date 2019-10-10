import { element, by, ElementFinder } from 'protractor';

export class OvertimeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-overtime div table .btn-danger'));
  title = element.all(by.css('jhi-overtime div h2#page-heading span')).first();

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

export class OvertimeUpdatePage {
  pageTitle = element(by.id('jhi-overtime-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  statusSelect = element(by.id('field_status'));
  detailsInput = element(by.id('field_details'));
  fromDateInput = element(by.id('field_fromDate'));
  toDateInput = element(by.id('field_toDate'));
  createdDateInput = element(by.id('field_createdDate'));
  overtimeInHoursInput = element(by.id('field_overtimeInHours'));
  totalBillableHoursInput = element(by.id('field_totalBillableHours'));
  totalCostingAmountInput = element(by.id('field_totalCostingAmount'));
  noteInput = element(by.id('field_note'));
  locationInput = element(by.id('field_location'));
  employeeNameInput = element(by.id('field_employeeName'));
  employeeEmailInput = element(by.id('field_employeeEmail'));
  departmentNameInput = element(by.id('field_departmentName'));
  departmentCodeInput = element(by.id('field_departmentCode'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
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

  async setDetailsInput(details) {
    await this.detailsInput.sendKeys(details);
  }

  async getDetailsInput() {
    return await this.detailsInput.getAttribute('value');
  }

  async setFromDateInput(fromDate) {
    await this.fromDateInput.sendKeys(fromDate);
  }

  async getFromDateInput() {
    return await this.fromDateInput.getAttribute('value');
  }

  async setToDateInput(toDate) {
    await this.toDateInput.sendKeys(toDate);
  }

  async getToDateInput() {
    return await this.toDateInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return await this.createdDateInput.getAttribute('value');
  }

  async setOvertimeInHoursInput(overtimeInHours) {
    await this.overtimeInHoursInput.sendKeys(overtimeInHours);
  }

  async getOvertimeInHoursInput() {
    return await this.overtimeInHoursInput.getAttribute('value');
  }

  async setTotalBillableHoursInput(totalBillableHours) {
    await this.totalBillableHoursInput.sendKeys(totalBillableHours);
  }

  async getTotalBillableHoursInput() {
    return await this.totalBillableHoursInput.getAttribute('value');
  }

  async setTotalCostingAmountInput(totalCostingAmount) {
    await this.totalCostingAmountInput.sendKeys(totalCostingAmount);
  }

  async getTotalCostingAmountInput() {
    return await this.totalCostingAmountInput.getAttribute('value');
  }

  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return await this.noteInput.getAttribute('value');
  }

  async setLocationInput(location) {
    await this.locationInput.sendKeys(location);
  }

  async getLocationInput() {
    return await this.locationInput.getAttribute('value');
  }

  async setEmployeeNameInput(employeeName) {
    await this.employeeNameInput.sendKeys(employeeName);
  }

  async getEmployeeNameInput() {
    return await this.employeeNameInput.getAttribute('value');
  }

  async setEmployeeEmailInput(employeeEmail) {
    await this.employeeEmailInput.sendKeys(employeeEmail);
  }

  async getEmployeeEmailInput() {
    return await this.employeeEmailInput.getAttribute('value');
  }

  async setDepartmentNameInput(departmentName) {
    await this.departmentNameInput.sendKeys(departmentName);
  }

  async getDepartmentNameInput() {
    return await this.departmentNameInput.getAttribute('value');
  }

  async setDepartmentCodeInput(departmentCode) {
    await this.departmentCodeInput.sendKeys(departmentCode);
  }

  async getDepartmentCodeInput() {
    return await this.departmentCodeInput.getAttribute('value');
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

export class OvertimeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-overtime-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-overtime'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
