import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-type.reducer';
import { IServiceType } from 'app/shared/model/service-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceTypeDetail = (props: IServiceTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ServiceType [<b>{serviceTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="serviceId">Service Id</span>
          </dt>
          <dd>{serviceTypeEntity.serviceId}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{serviceTypeEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/service-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-type/${serviceTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceType }: IRootState) => ({
  serviceTypeEntity: serviceType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceTypeDetail);
