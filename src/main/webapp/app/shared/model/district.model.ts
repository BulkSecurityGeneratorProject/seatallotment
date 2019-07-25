export interface IDistrict {
  id?: string;
  name?: string;
  active?: boolean;
}

export class District implements IDistrict {
  constructor(public id?: string, public name?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
