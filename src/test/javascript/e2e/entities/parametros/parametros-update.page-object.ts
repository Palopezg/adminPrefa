import { element, by, ElementFinder } from 'protractor';

export default class ParametrosUpdatePage {
  pageTitle: ElementFinder = element(by.id('adminPrefaApp.parametros.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  parametroInput: ElementFinder = element(by.css('input#parametros-parametro'));
  valorInput: ElementFinder = element(by.css('input#parametros-valor'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setParametroInput(parametro) {
    await this.parametroInput.sendKeys(parametro);
  }

  async getParametroInput() {
    return this.parametroInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return this.valorInput.getAttribute('value');
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
