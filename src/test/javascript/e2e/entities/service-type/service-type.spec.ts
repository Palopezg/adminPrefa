import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceTypeComponentsPage, { ServiceTypeDeleteDialog } from './service-type.page-object';
import ServiceTypeUpdatePage from './service-type-update.page-object';
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

describe('ServiceType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceTypeComponentsPage: ServiceTypeComponentsPage;
  let serviceTypeUpdatePage: ServiceTypeUpdatePage;
  let serviceTypeDeleteDialog: ServiceTypeDeleteDialog;
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

  it('should load ServiceTypes', async () => {
    await navBarPage.getEntityPage('service-type');
    serviceTypeComponentsPage = new ServiceTypeComponentsPage();
    expect(await serviceTypeComponentsPage.title.getText()).to.match(/Service Types/);

    expect(await serviceTypeComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([serviceTypeComponentsPage.noRecords, serviceTypeComponentsPage.table]);

    beforeRecordsCount = (await isVisible(serviceTypeComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceTypeComponentsPage.table);
  });

  it('should load create ServiceType page', async () => {
    await serviceTypeComponentsPage.createButton.click();
    serviceTypeUpdatePage = new ServiceTypeUpdatePage();
    expect(await serviceTypeUpdatePage.getPageTitle().getText()).to.match(/Create or edit a ServiceType/);
    await serviceTypeUpdatePage.cancel();
  });

  it('should create and save ServiceTypes', async () => {
    await serviceTypeComponentsPage.createButton.click();
    await serviceTypeUpdatePage.setServiceIdInput('serviceId');
    expect(await serviceTypeUpdatePage.getServiceIdInput()).to.match(/serviceId/);
    await serviceTypeUpdatePage.setDescripcionInput('descripcion');
    expect(await serviceTypeUpdatePage.getDescripcionInput()).to.match(/descripcion/);
    await waitUntilDisplayed(serviceTypeUpdatePage.saveButton);
    await serviceTypeUpdatePage.save();
    await waitUntilHidden(serviceTypeUpdatePage.saveButton);
    expect(await isVisible(serviceTypeUpdatePage.saveButton)).to.be.false;

    expect(await serviceTypeComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(serviceTypeComponentsPage.table);

    await waitUntilCount(serviceTypeComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceTypeComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last ServiceType', async () => {
    const deleteButton = serviceTypeComponentsPage.getDeleteButton(serviceTypeComponentsPage.records.last());
    await click(deleteButton);

    serviceTypeDeleteDialog = new ServiceTypeDeleteDialog();
    await waitUntilDisplayed(serviceTypeDeleteDialog.deleteModal);
    expect(await serviceTypeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/adminPrefaApp.serviceType.delete.question/);
    await serviceTypeDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(serviceTypeDeleteDialog.deleteModal);

    expect(await isVisible(serviceTypeDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([serviceTypeComponentsPage.noRecords, serviceTypeComponentsPage.table]);

    const afterCount = (await isVisible(serviceTypeComponentsPage.noRecords)) ? 0 : await getRecordsCount(serviceTypeComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
