import { element, by, ElementFinder } from 'protractor';

export class OvertimeHistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-overtime-history div table .btn-danger'));
  title = element.all(by.css('jhi-overtime-history div h2#page-heading span')).first();

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

export class OvertimeHistoryUpdatePage {
  pageTitle = element(by.id('jhi-overtime-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dateInput = element(by.id('field_date'));
  statusSelect = element(by.id('field_status'));
  changeTypeInput = element(by.id('field_changeType'));
  whoInput = element(by.id('field_who'));
  overtimeSelect = element(by.id('field_overtime'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return await this.dateInput.getAttribute('value');
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

  async setChangeTypeInput(changeType) {
    await this.changeTypeInput.sendKeys(changeType);
  }

  async getChangeTypeInput() {
    return await this.changeTypeInput.getAttribute('value');
  }

  async setWhoInput(who) {
    await this.whoInput.sendKeys(who);
  }

  async getWhoInput() {
    return await this.whoInput.getAttribute('value');
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

export class OvertimeHistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-overtimeHistory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-overtimeHistory'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
