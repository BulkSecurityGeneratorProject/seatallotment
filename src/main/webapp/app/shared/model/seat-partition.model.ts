export interface ISeatPartition {
  id?: string;
  district?: string;
  total?: number;
  vacant?: number;
  occupied?: number;
}

export class SeatPartition implements ISeatPartition {
  constructor(public id?: string, public district?: string, public total?: number, public vacant?: number, public occupied?: number) {}
}
