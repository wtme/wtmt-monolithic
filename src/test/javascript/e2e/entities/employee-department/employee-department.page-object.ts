import { element, by, ElementFinder } from 'protractor';

export class EmployeeDepartmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee-department div table .btn-danger'));
  title = element.all(by.css('jhi-employee-department div h2#page-heading span')).first();

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

export class EmployeeDepartmentUpdatePage {
  pageTitle = element(by.id('jhi-employee-department-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  employeeSelect = element(by.id('field_employee'));
  departmentSelect = element(by.id('field_department'));

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

export class EmployeeDepartmentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employeeDepartment-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employeeDepartment'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
