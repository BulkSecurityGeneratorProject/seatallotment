/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { SeatPartitionDetailComponent } from 'app/entities/seat-partition/seat-partition-detail.component';
import { SeatPartition } from 'app/shared/model/seat-partition.model';

describe('Component Tests', () => {
  describe('SeatPartition Management Detail Component', () => {
    let comp: SeatPartitionDetailComponent;
    let fixture: ComponentFixture<SeatPartitionDetailComponent>;
    const route = ({ data: of({ seatPartition: new SeatPartition('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [SeatPartitionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SeatPartitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SeatPartitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.seatPartition).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
  });
});
