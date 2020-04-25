import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Characteristic from './characteristic';
import ServiceType from './service-type';
import Parametros from './parametros';
import TipoDeco from './tipo-deco';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}characteristic`} component={Characteristic} />
      <ErrorBoundaryRoute path={`${match.url}service-type`} component={ServiceType} />
      <ErrorBoundaryRoute path={`${match.url}parametros`} component={Parametros} />
      <ErrorBoundaryRoute path={`${match.url}tipo-deco`} component={TipoDeco} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
