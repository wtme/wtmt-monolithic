import { element, by, ElementFinder } from 'protractor';

export class OvertimeCommentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-overtime-comment div table .btn-danger'));
  title = element.all(by.css('jhi-overtime-comment div h2#page-heading span')).first();

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

export class OvertimeCommentUpdatePage {
  pageTitle = element(by.id('jhi-overtime-comment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  commentInput = element(by.id('field_comment'));
  createdDateInput = element(by.id('field_createdDate'));
  employeeNameInput = element(by.id('field_employeeName'));
  employeeEmailInput = element(by.id('field_employeeEmail'));
  overtimeSelect = element(by.id('field_overtime'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCommentInput(comment) {
    await this.commentInput.sendKeys(comment);
  }

  async getCommentInput() {
    return await this.commentInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return await this.createdDateInput.getAttribute('value');
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

  async overtimeSelectLastOption(timeout?: number) {
    await this.overtimeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async overtimeSelectOption(option) {
    await this.overtimeSelect.sendKeys(option);
  }

  getOvertimeSelect(): ElementFinder {
    return this.overtimeSelect;
  }

  async getOvertimeSelectedOption() {
    return await this.overtimeSelect.element(by.css('option:checked')).getText();
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

export class OvertimeCommentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-overtimeComment-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-overtimeComment'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
