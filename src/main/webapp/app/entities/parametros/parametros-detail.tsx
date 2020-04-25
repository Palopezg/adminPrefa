import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './parametros.reducer';
import { IParametros } from 'app/shared/model/parametros.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParametrosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ParametrosDetail = (props: IParametrosDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { parametrosEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Parametros [<b>{parametrosEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="parametro">Parametro</span>
          </dt>
          <dd>{parametrosEntity.parametro}</dd>
          <dt>
            <span id="valor">Valor</span>
          </dt>
          <dd>{parametrosEntity.valor}</dd>
        </dl>
        <Button tag={Link} to="/parametros" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parametros/${parametrosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ parametros }: IRootState) => ({
  parametrosEntity: parametros.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ParametrosDetail);
