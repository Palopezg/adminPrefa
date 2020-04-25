import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ParametrosComponentsPage, { ParametrosDeleteDialog } from './parametros.page-object';
import ParametrosUpdatePage from './parametros-update.page-object';
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

describe('Parametros e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let parametrosComponentsPage: ParametrosComponentsPage;
  let parametrosUpdatePage: ParametrosUpdatePage;
  let parametrosDeleteDialog: ParametrosDeleteDialog;
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

  it('should load Parametros', async () => {
    await navBarPage.getEntityPage('parametros');
    parametrosComponentsPage = new ParametrosComponentsPage();
    expect(await parametrosComponentsPage.title.getText()).to.match(/Parametros/);

    expect(await parametrosComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([parametrosComponentsPage.noRecords, parametrosComponentsPage.table]);

    beforeRecordsCount = (await isVisible(parametrosComponentsPage.noRecords)) ? 0 : await getRecordsCount(parametrosComponentsPage.table);
  });

  it('should load create Parametros page', async () => {
    await parametrosComponentsPage.createButton.click();
    parametrosUpdatePage = new ParametrosUpdatePage();
    expect(await parametrosUpdatePage.getPageTitle().getText()).to.match(/Create or edit a Parametros/);
    await parametrosUpdatePage.cancel();
  });

  it('should create and save Parametros', async () => {
    await parametrosComponentsPage.createButton.click();
    await parametrosUpdatePage.setParametroInput('parametro');
    expect(await parametrosUpdatePage.getParametroInput()).to.match(/parametro/);
    await parametrosUpdatePage.setValorInput('valor');
    expect(await parametrosUpdatePage.getValorInput()).to.match(/valor/);
    await waitUntilDisplayed(parametrosUpdatePage.saveButton);
    await parametrosUpdatePage.save();
    await waitUntilHidden(parametrosUpdatePage.saveButton);
    expect(await isVisible(parametrosUpdatePage.saveButton)).to.be.false;

    expect(await parametrosComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(parametrosComponentsPage.table);

    await waitUntilCount(parametrosComponentsPage.records, beforeRecordsCount + 1);
    expect(await parametrosComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last Parametros', async () => {
    const deleteButton = parametrosComponentsPage.getDeleteButton(parametrosComponentsPage.records.last());
    await click(deleteButton);

    parametrosDeleteDialog = new ParametrosDeleteDialog();
    await waitUntilDisplayed(parametrosDeleteDialog.deleteModal);
    expect(await parametrosDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/adminPrefaApp.parametros.delete.question/);
    await parametrosDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(parametrosDeleteDialog.deleteModal);

    expect(await isVisible(parametrosDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([parametrosComponentsPage.noRecords, parametrosComponentsPage.table]);

    const afterCount = (await isVisible(parametrosComponentsPage.noRecords)) ? 0 : await getRecordsCount(parametrosComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
