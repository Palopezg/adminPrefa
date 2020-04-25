import { element, by, ElementFinder } from 'protractor';

export default class ServiceTypeUpdatePage {
  pageTitle: ElementFinder = element(by.id('adminPrefaApp.serviceType.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  serviceIdInput: ElementFinder = element(by.css('input#service-type-serviceId'));
  descripcionInput: ElementFinder = element(by.css('input#service-type-descripcion'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setServiceIdInput(serviceId) {
    await this.serviceIdInput.sendKeys(serviceId);
  }

  async getServiceIdInput() {
    return this.serviceIdInput.getAttribute('value');
  }

  async setDescripcionInput(descripcion) {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput() {
    return this.descripcionInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
