export interface ISpeciality {
  id?: string;
  name?: string;
  active?: boolean;
}

export class Speciality implements ISpeciality {
  constructor(public id?: string, public name?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
