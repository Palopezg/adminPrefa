import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceType, defaultValue } from 'app/shared/model/service-type.model';

export const ACTION_TYPES = {
  FETCH_SERVICETYPE_LIST: 'serviceType/FETCH_SERVICETYPE_LIST',
  FETCH_SERVICETYPE: 'serviceType/FETCH_SERVICETYPE',
  CREATE_SERVICETYPE: 'serviceType/CREATE_SERVICETYPE',
  UPDATE_SERVICETYPE: 'serviceType/UPDATE_SERVICETYPE',
  DELETE_SERVICETYPE: 'serviceType/DELETE_SERVICETYPE',
  RESET: 'serviceType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ServiceTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceTypeState = initialState, action): ServiceTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICETYPE):
    case REQUEST(ACTION_TYPES.DELETE_SERVICETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICETYPE):
    case FAILURE(ACTION_TYPES.CREATE_SERVICETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICETYPE):
    case FAILURE(ACTION_TYPES.DELETE_SERVICETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICETYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICETYPE):
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

const apiUrl = 'api/service-types';

// Actions

export const getEntities: ICrudGetAllAction<IServiceType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICETYPE_LIST,
    payload: axios.get<IServiceType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IServiceType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICETYPE,
    payload: axios.get<IServiceType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IServiceType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICETYPE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
