import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tipo-deco.reducer';
import { ITipoDeco } from 'app/shared/model/tipo-deco.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITipoDecoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TipoDecoDetail = (props: ITipoDecoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tipoDecoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          TipoDeco [<b>{tipoDecoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tipoDeco">Tipo Deco</span>
          </dt>
          <dd>{tipoDecoEntity.tipoDeco}</dd>
          <dt>
            <span id="tecnologia">Tecnologia</span>
          </dt>
          <dd>{tipoDecoEntity.tecnologia}</dd>
          <dt>
            <span id="multicast">Multicast</span>
          </dt>
          <dd>{tipoDecoEntity.multicast}</dd>
        </dl>
        <Button tag={Link} to="/tipo-deco" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-deco/${tipoDecoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tipoDeco }: IRootState) => ({
  tipoDecoEntity: tipoDeco.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoDecoDetail);
