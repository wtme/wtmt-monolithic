import { element, by, ElementFinder } from 'protractor';

export class DepartmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-department div table .btn-danger'));
  title = element.all(by.css('jhi-department div h2#page-heading span')).first();

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

export class DepartmentUpdatePage {
  pageTitle = element(by.id('jhi-department-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  disabledInput = element(by.id('field_disabled'));
  notesInput = element(by.id('field_notes'));
  locationSelect = element(by.id('field_location'));
  parentSelect = element(by.id('field_parent'));

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

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  getDisabledInput(timeout?: number) {
    return this.disabledInput;
  }
  async setNotesInput(notes) {
    await this.notesInput.sendKeys(notes);
  }

  async getNotesInput() {
    return await this.notesInput.getAttribute('value');
  }

  async locationSelectLastOption(timeout?: number) {
    await this.locationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async locationSelectOption(option) {
    await this.locationSelect.sendKeys(option);
  }

  getLocationSelect(): ElementFinder {
    return this.locationSelect;
  }

  async getLocationSelectedOption() {
    return await this.locationSelect.element(by.css('option:checked')).getText();
  }

  async parentSelectLastOption(timeout?: number) {
    await this.parentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async parentSelectOption(option) {
    await this.parentSelect.sendKeys(option);
  }

  getParentSelect(): ElementFinder {
    return this.parentSelect;
  }

  async getParentSelectedOption() {
    return await this.parentSelect.element(by.css('option:checked')).getText();
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

export class DepartmentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-department-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-department'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
