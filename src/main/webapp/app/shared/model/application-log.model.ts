import { Moment } from 'moment';

export interface IApplicationLog {
  id?: string;
  application_no?: string;
  action?: string;
  action_by?: string;
  action_date?: Moment;
}

export class ApplicationLog implements IApplicationLog {
  constructor(
    public id?: string,
    public application_no?: string,
    public action?: string,
    public action_by?: string,
    public action_date?: Moment
  ) {}
}
