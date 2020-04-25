import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoDeco from './tipo-deco';
import TipoDecoDetail from './tipo-deco-detail';
import TipoDecoUpdate from './tipo-deco-update';
import TipoDecoDeleteDialog from './tipo-deco-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoDecoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoDecoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoDecoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoDecoDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoDeco} />
    </Switch>
  </>
);

export default Routes;
