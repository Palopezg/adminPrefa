import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './characteristic.reducer';
import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICharacteristicDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CharacteristicDetail = (props: ICharacteristicDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { characteristicEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Characteristic [<b>{characteristicEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="characteristicId">Characteristic Id</span>
          </dt>
          <dd>{characteristicEntity.characteristicId}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{characteristicEntity.descripcion}</dd>
          <dt>Service Type</dt>
          <dd>{characteristicEntity.serviceType ? characteristicEntity.serviceType.descripcion : ''}</dd>
        </dl>
        <Button tag={Link} to="/characteristic" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/characteristic/${characteristicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ characteristic }: IRootState) => ({
  characteristicEntity: characteristic.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CharacteristicDetail);
