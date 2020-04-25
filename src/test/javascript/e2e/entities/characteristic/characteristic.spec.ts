import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CharacteristicComponentsPage, { CharacteristicDeleteDialog } from './characteristic.page-object';
import CharacteristicUpdatePage from './characteristic-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible
} from '../../util/utils';

const expect = chai.expect;

describe('Characteristic e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let characteristicComponentsPage: CharacteristicComponentsPage;
  let characteristicUpdatePage: CharacteristicUpdatePage;
  let characteristicDeleteDialog: CharacteristicDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load Characteristics', async () => {
    await navBarPage.getEntityPage('characteristic');
    characteristicComponentsPage = new CharacteristicComponentsPage();
    expect(await characteristicComponentsPage.title.getText()).to.match(/Characteristics/);

    expect(await characteristicComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([characteristicComponentsPage.noRecords, characteristicComponentsPage.table]);

    beforeRecordsCount = (await isVisible(characteristicComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(characteristicComponentsPage.table);
  });

  it('should load create Characteristic page', async () => {
    await characteristicComponentsPage.createButton.click();
    characteristicUpdatePage = new CharacteristicUpdatePage();
    expect(await characteristicUpdatePage.getPageTitle().getText()).to.match(/Create or edit a Characteristic/);
    await characteristicUpdatePage.cancel();
  });

  it('should create and save Characteristics', async () => {
    await characteristicComponentsPage.createButton.click();
    await characteristicUpdatePage.setCharacteristicIdInput('characteristicId');
    expect(await characteristicUpdatePage.getCharacteristicIdInput()).to.match(/characteristicId/);
    await characteristicUpdatePage.setDescripcionInput('descripcion');
    expect(await characteristicUpdatePage.getDescripcionInput()).to.match(/descripcion/);
    await characteristicUpdatePage.serviceTypeSelectLastOption();
    await waitUntilDisplayed(characteristicUpdatePage.saveButton);
    await characteristicUpdatePage.save();
    await waitUntilHidden(characteristicUpdatePage.saveButton);
    expect(await isVisible(characteristicUpdatePage.saveButton)).to.be.false;

    expect(await characteristicComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(characteristicComponentsPage.table);

    await waitUntilCount(characteristicComponentsPage.records, beforeRecordsCount + 1);
    expect(await characteristicComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last Characteristic', async () => {
    const deleteButton = characteristicComponentsPage.getDeleteButton(characteristicComponentsPage.records.last());
    await click(deleteButton);

    characteristicDeleteDialog = new CharacteristicDeleteDialog();
    await waitUntilDisplayed(characteristicDeleteDialog.deleteModal);
    expect(await characteristicDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/adminPrefaApp.characteristic.delete.question/);
    await characteristicDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(characteristicDeleteDialog.deleteModal);

    expect(await isVisible(characteristicDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([characteristicComponentsPage.noRecords, characteristicComponentsPage.table]);

    const afterCount = (await isVisible(characteristicComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(characteristicComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
