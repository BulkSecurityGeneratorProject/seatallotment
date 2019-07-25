import { Moment } from 'moment';

export interface IApplication {
  id?: string;
  application_no?: string;
  applicant_name?: string;
  applicant_email?: string;
  applicant_mobile?: string;
  applicant_dob?: Moment;
  applicant_address?: string;
  course?: string;
  institute?: string;
  speciality?: string;
  course_doc?: string;
  first_preference?: string;
  second_preference?: string;
  third_preference?: string;
  entryby?: string;
  entry_date?: Moment;
  status?: string;
}

export class Application implements IApplication {
  constructor(
    public id?: string,
    public application_no?: string,
    public applicant_name?: string,
    public applicant_email?: string,
    public applicant_mobile?: string,
    public applicant_dob?: Moment,
    public applicant_address?: string,
    public course?: string,
    public institute?: string,
    public speciality?: string,
    public course_doc?: string,
    public first_preference?: string,
    public second_preference?: string,
    public third_preference?: string,
    public entryby?: string,
    public entry_date?: Moment,
    public status?: string
  ) {}
}
