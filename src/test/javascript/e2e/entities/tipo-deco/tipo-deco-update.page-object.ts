import { element, by, ElementFinder } from 'protractor';

export default class TipoDecoUpdatePage {
  pageTitle: ElementFinder = element(by.id('adminPrefaApp.tipoDeco.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tipoDecoInput: ElementFinder = element(by.css('input#tipo-deco-tipoDeco'));
  tecnologiaSelect: ElementFinder = element(by.css('select#tipo-deco-tecnologia'));
  multicastInput: ElementFinder = element(by.css('input#tipo-deco-multicast'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTipoDecoInput(tipoDeco) {
    await this.tipoDecoInput.sendKeys(tipoDeco);
  }

  async getTipoDecoInput() {
    return this.tipoDecoInput.getAttribute('value');
  }

  async setTecnologiaSelect(tecnologia) {
    await this.tecnologiaSelect.sendKeys(tecnologia);
  }

  async getTecnologiaSelect() {
    return this.tecnologiaSelect.element(by.css('option:checked')).getText();
  }

  async tecnologiaSelectLastOption() {
    await this.tecnologiaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async setMulticastInput(multicast) {
    await this.multicastInput.sendKeys(multicast);
  }

  async getMulticastInput() {
    return this.multicastInput.getAttribute('value');
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
