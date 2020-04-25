import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICharacteristic, defaultValue } from 'app/shared/model/characteristic.model';

export const ACTION_TYPES = {
  FETCH_CHARACTERISTIC_LIST: 'characteristic/FETCH_CHARACTERISTIC_LIST',
  FETCH_CHARACTERISTIC: 'characteristic/FETCH_CHARACTERISTIC',
  CREATE_CHARACTERISTIC: 'characteristic/CREATE_CHARACTERISTIC',
  UPDATE_CHARACTERISTIC: 'characteristic/UPDATE_CHARACTERISTIC',
  DELETE_CHARACTERISTIC: 'characteristic/DELETE_CHARACTERISTIC',
  RESET: 'characteristic/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICharacteristic>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CharacteristicState = Readonly<typeof initialState>;

// Reducer

export default (state: CharacteristicState = initialState, action): CharacteristicState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CHARACTERISTIC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHARACTERISTIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CHARACTERISTIC):
    case REQUEST(ACTION_TYPES.UPDATE_CHARACTERISTIC):
    case REQUEST(ACTION_TYPES.DELETE_CHARACTERISTIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CHARACTERISTIC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHARACTERISTIC):
    case FAILURE(ACTION_TYPES.CREATE_CHARACTERISTIC):
    case FAILURE(ACTION_TYPES.UPDATE_CHARACTERISTIC):
    case FAILURE(ACTION_TYPES.DELETE_CHARACTERISTIC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHARACTERISTIC_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHARACTERISTIC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHARACTERISTIC):
    case SUCCESS(ACTION_TYPES.UPDATE_CHARACTERISTIC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHARACTERISTIC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/characteristics';

// Actions

export const getEntities: ICrudGetAllAction<ICharacteristic> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CHARACTERISTIC_LIST,
    payload: axios.get<ICharacteristic>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICharacteristic> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHARACTERISTIC,
    payload: axios.get<ICharacteristic>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICharacteristic> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHARACTERISTIC,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICharacteristic> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHARACTERISTIC,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICharacteristic> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHARACTERISTIC,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
