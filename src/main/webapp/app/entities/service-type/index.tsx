import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceType from './service-type';
import ServiceTypeDetail from './service-type-detail';
import ServiceTypeUpdate from './service-type-update';
import ServiceTypeDeleteDialog from './service-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceTypeDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceType} />
    </Switch>
  </>
);

export default Routes;
