import { Moment } from 'moment';

export interface IApplicationlog {
  id?: string;
  application_no?: string;
  action?: string;
  action_by?: string;
  action_date?: Moment;
}

export class Applicationlog implements IApplicationlog {
  constructor(
    public id?: string,
    public application_no?: string,
    public action?: string,
    public action_by?: string,
    public action_date?: Moment
  ) {}
}
