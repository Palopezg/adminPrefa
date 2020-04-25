import { Tecnologia } from 'app/shared/model/enumerations/tecnologia.model';

export interface ITipoDeco {
  id?: number;
  tipoDeco?: string;
  tecnologia?: Tecnologia;
  multicast?: string;
}

export const defaultValue: Readonly<ITipoDeco> = {};
