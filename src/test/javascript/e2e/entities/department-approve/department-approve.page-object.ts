import { element, by, ElementFinder } from 'protractor';

export class DepartmentApproveComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-department-approve div table .btn-danger'));
  title = element.all(by.css('jhi-department-approve div h2#page-heading span')).first();

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

export class DepartmentApproveUpdatePage {
  pageTitle = element(by.id('jhi-department-approve-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  employeeSelect = element(by.id('field_employee'));
  departmentSelect = element(by.id('field_department'));
  departmentRoleSelect = element(by.id('field_departmentRole'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async employeeSelectLastOption(timeout?: number) {
    await this.employeeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async employeeSelectOption(option) {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect(): ElementFinder {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption() {
    return await this.employeeSelect.element(by.css('option:checked')).getText();
  }

  async departmentSelectLastOption(timeout?: number) {
    await this.departmentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async departmentSelectOption(option) {
    await this.departmentSelect.sendKeys(option);
  }

  getDepartmentSelect(): ElementFinder {
    return this.departmentSelect;
  }

  async getDepartmentSelectedOption() {
    return await this.departmentSelect.element(by.css('option:checked')).getText();
  }

  async departmentRoleSelectLastOption(timeout?: number) {
    await this.departmentRoleSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async departmentRoleSelectOption(option) {
    await this.departmentRoleSelect.sendKeys(option);
  }

  getDepartmentRoleSelect(): ElementFinder {
    return this.departmentRoleSelect;
  }

  async getDepartmentRoleSelectedOption() {
    return await this.departmentRoleSelect.element(by.css('option:checked')).getText();
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

export class DepartmentApproveDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-departmentApprove-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-departmentApprove'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
