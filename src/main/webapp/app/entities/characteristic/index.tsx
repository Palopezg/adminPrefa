import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Characteristic from './characteristic';
import CharacteristicDetail from './characteristic-detail';
import CharacteristicUpdate from './characteristic-update';
import CharacteristicDeleteDialog from './characteristic-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CharacteristicDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CharacteristicUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CharacteristicUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CharacteristicDetail} />
      <ErrorBoundaryRoute path={match.url} component={Characteristic} />
    </Switch>
  </>
);

export default Routes;
