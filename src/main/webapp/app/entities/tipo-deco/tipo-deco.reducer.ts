import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoDeco, defaultValue } from 'app/shared/model/tipo-deco.model';

export const ACTION_TYPES = {
  FETCH_TIPODECO_LIST: 'tipoDeco/FETCH_TIPODECO_LIST',
  FETCH_TIPODECO: 'tipoDeco/FETCH_TIPODECO',
  CREATE_TIPODECO: 'tipoDeco/CREATE_TIPODECO',
  UPDATE_TIPODECO: 'tipoDeco/UPDATE_TIPODECO',
  DELETE_TIPODECO: 'tipoDeco/DELETE_TIPODECO',
  RESET: 'tipoDeco/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoDeco>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TipoDecoState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoDecoState = initialState, action): TipoDecoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPODECO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPODECO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPODECO):
    case REQUEST(ACTION_TYPES.UPDATE_TIPODECO):
    case REQUEST(ACTION_TYPES.DELETE_TIPODECO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPODECO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPODECO):
    case FAILURE(ACTION_TYPES.CREATE_TIPODECO):
    case FAILURE(ACTION_TYPES.UPDATE_TIPODECO):
    case FAILURE(ACTION_TYPES.DELETE_TIPODECO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPODECO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPODECO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPODECO):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPODECO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPODECO):
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

const apiUrl = 'api/tipo-decos';

// Actions

export const getEntities: ICrudGetAllAction<ITipoDeco> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TIPODECO_LIST,
    payload: axios.get<ITipoDeco>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITipoDeco> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPODECO,
    payload: axios.get<ITipoDeco>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITipoDeco> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPODECO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoDeco> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPODECO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoDeco> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPODECO,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
