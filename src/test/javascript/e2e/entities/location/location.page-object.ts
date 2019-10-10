import { element, by, ElementFinder } from 'protractor';

export class LocationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-location div table .btn-danger'));
  title = element.all(by.css('jhi-location div h2#page-heading span')).first();

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

export class LocationUpdatePage {
  pageTitle = element(by.id('jhi-location-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  streetAddressInput = element(by.id('field_streetAddress'));
  postalCodeInput = element(by.id('field_postalCode'));
  cityInput = element(by.id('field_city'));
  stateProvinceInput = element(by.id('field_stateProvince'));

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

  async setStreetAddressInput(streetAddress) {
    await this.streetAddressInput.sendKeys(streetAddress);
  }

  async getStreetAddressInput() {
    return await this.streetAddressInput.getAttribute('value');
  }

  async setPostalCodeInput(postalCode) {
    await this.postalCodeInput.sendKeys(postalCode);
  }

  async getPostalCodeInput() {
    return await this.postalCodeInput.getAttribute('value');
  }

  async setCityInput(city) {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput() {
    return await this.cityInput.getAttribute('value');
  }

  async setStateProvinceInput(stateProvince) {
    await this.stateProvinceInput.sendKeys(stateProvince);
  }

  async getStateProvinceInput() {
    return await this.stateProvinceInput.getAttribute('value');
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

export class LocationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-location-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-location'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
