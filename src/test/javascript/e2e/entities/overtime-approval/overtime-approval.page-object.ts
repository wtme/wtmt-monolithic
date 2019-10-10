import { element, by, ElementFinder } from 'protractor';

export class OvertimeApprovalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-overtime-approval div table .btn-danger'));
  title = element.all(by.css('jhi-overtime-approval div h2#page-heading span')).first();

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

export class OvertimeApprovalUpdatePage {
  pageTitle = element(by.id('jhi-overtime-approval-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  approverNameInput = element(by.id('field_approverName'));
  approverEmailInput = element(by.id('field_approverEmail'));
  overtimeSelect = element(by.id('field_overtime'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setApproverNameInput(approverName) {
    await this.approverNameInput.sendKeys(approverName);
  }

  async getApproverNameInput() {
    return await this.approverNameInput.getAttribute('value');
  }

  async setApproverEmailInput(approverEmail) {
    await this.approverEmailInput.sendKeys(approverEmail);
  }

  async getApproverEmailInput() {
    return await this.approverEmailInput.getAttribute('value');
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

export class OvertimeApprovalDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-overtimeApproval-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-overtimeApproval'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
