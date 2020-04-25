import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceType } from 'app/shared/model/service-type.model';
import { getEntities as getServiceTypes } from 'app/entities/service-type/service-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './characteristic.reducer';
import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICharacteristicUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CharacteristicUpdate = (props: ICharacteristicUpdateProps) => {
  const [serviceTypeId, setServiceTypeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { characteristicEntity, serviceTypes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/characteristic' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceTypes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...characteristicEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adminPrefaApp.characteristic.home.createOrEditLabel">Create or edit a Characteristic</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : characteristicEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="characteristic-id">ID</Label>
                  <AvInput id="characteristic-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="characteristicIdLabel" for="characteristic-characteristicId">
                  Characteristic Id
                </Label>
                <AvField
                  id="characteristic-characteristicId"
                  type="text"
                  name="characteristicId"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descripcionLabel" for="characteristic-descripcion">
                  Descripcion
                </Label>
                <AvField id="characteristic-descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup>
                <Label for="characteristic-serviceType">Service Type</Label>
                <AvInput id="characteristic-serviceType" type="select" className="form-control" name="serviceType.id">
                  <option value="" key="0" />
                  {serviceTypes
                    ? serviceTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/characteristic" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  serviceTypes: storeState.serviceType.entities,
  characteristicEntity: storeState.characteristic.entity,
  loading: storeState.characteristic.loading,
  updating: storeState.characteristic.updating,
  updateSuccess: storeState.characteristic.updateSuccess
});

const mapDispatchToProps = {
  getServiceTypes,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CharacteristicUpdate);
