import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './tipo-deco.reducer';
import { ITipoDeco } from 'app/shared/model/tipo-deco.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITipoDecoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TipoDecoUpdate = (props: ITipoDecoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { tipoDecoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/tipo-deco' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...tipoDecoEntity,
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
          <h2 id="adminPrefaApp.tipoDeco.home.createOrEditLabel">Create or edit a TipoDeco</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : tipoDecoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="tipo-deco-id">ID</Label>
                  <AvInput id="tipo-deco-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tipoDecoLabel" for="tipo-deco-tipoDeco">
                  Tipo Deco
                </Label>
                <AvField
                  id="tipo-deco-tipoDeco"
                  type="text"
                  name="tipoDeco"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tecnologiaLabel" for="tipo-deco-tecnologia">
                  Tecnologia
                </Label>
                <AvInput
                  id="tipo-deco-tecnologia"
                  type="select"
                  className="form-control"
                  name="tecnologia"
                  value={(!isNew && tipoDecoEntity.tecnologia) || 'FTTH'}
                >
                  <option value="FTTH">FTTH</option>
                  <option value="HFC">HFC</option>
                  <option value="COBRE">COBRE</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="multicastLabel" for="tipo-deco-multicast">
                  Multicast
                </Label>
                <AvField
                  id="tipo-deco-multicast"
                  type="text"
                  name="multicast"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' }
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/tipo-deco" replace color="info">
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
  tipoDecoEntity: storeState.tipoDeco.entity,
  loading: storeState.tipoDeco.loading,
  updating: storeState.tipoDeco.updating,
  updateSuccess: storeState.tipoDeco.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoDecoUpdate);
