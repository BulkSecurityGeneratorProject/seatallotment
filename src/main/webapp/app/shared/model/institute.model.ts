export interface IInstitute {
  id?: string;
  name?: string;
  type?: string;
}

export class Institute implements IInstitute {
  constructor(public id?: string, public name?: string, public type?: string) {}
}
