import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TipoDecoComponentsPage, { TipoDecoDeleteDialog } from './tipo-deco.page-object';
import TipoDecoUpdatePage from './tipo-deco-update.page-object';
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

describe('TipoDeco e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoDecoComponentsPage: TipoDecoComponentsPage;
  let tipoDecoUpdatePage: TipoDecoUpdatePage;
  let tipoDecoDeleteDialog: TipoDecoDeleteDialog;
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

  it('should load TipoDecos', async () => {
    await navBarPage.getEntityPage('tipo-deco');
    tipoDecoComponentsPage = new TipoDecoComponentsPage();
    expect(await tipoDecoComponentsPage.title.getText()).to.match(/Tipo Decos/);

    expect(await tipoDecoComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([tipoDecoComponentsPage.noRecords, tipoDecoComponentsPage.table]);

    beforeRecordsCount = (await isVisible(tipoDecoComponentsPage.noRecords)) ? 0 : await getRecordsCount(tipoDecoComponentsPage.table);
  });

  it('should load create TipoDeco page', async () => {
    await tipoDecoComponentsPage.createButton.click();
    tipoDecoUpdatePage = new TipoDecoUpdatePage();
    expect(await tipoDecoUpdatePage.getPageTitle().getText()).to.match(/Create or edit a TipoDeco/);
    await tipoDecoUpdatePage.cancel();
  });

  it('should create and save TipoDecos', async () => {
    await tipoDecoComponentsPage.createButton.click();
    await tipoDecoUpdatePage.setTipoDecoInput('tipoDeco');
    expect(await tipoDecoUpdatePage.getTipoDecoInput()).to.match(/tipoDeco/);
    await tipoDecoUpdatePage.tecnologiaSelectLastOption();
    await tipoDecoUpdatePage.setMulticastInput('multicast');
    expect(await tipoDecoUpdatePage.getMulticastInput()).to.match(/multicast/);
    await waitUntilDisplayed(tipoDecoUpdatePage.saveButton);
    await tipoDecoUpdatePage.save();
    await waitUntilHidden(tipoDecoUpdatePage.saveButton);
    expect(await isVisible(tipoDecoUpdatePage.saveButton)).to.be.false;

    expect(await tipoDecoComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(tipoDecoComponentsPage.table);

    await waitUntilCount(tipoDecoComponentsPage.records, beforeRecordsCount + 1);
    expect(await tipoDecoComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last TipoDeco', async () => {
    const deleteButton = tipoDecoComponentsPage.getDeleteButton(tipoDecoComponentsPage.records.last());
    await click(deleteButton);

    tipoDecoDeleteDialog = new TipoDecoDeleteDialog();
    await waitUntilDisplayed(tipoDecoDeleteDialog.deleteModal);
    expect(await tipoDecoDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/adminPrefaApp.tipoDeco.delete.question/);
    await tipoDecoDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(tipoDecoDeleteDialog.deleteModal);

    expect(await isVisible(tipoDecoDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([tipoDecoComponentsPage.noRecords, tipoDecoComponentsPage.table]);

    const afterCount = (await isVisible(tipoDecoComponentsPage.noRecords)) ? 0 : await getRecordsCount(tipoDecoComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
