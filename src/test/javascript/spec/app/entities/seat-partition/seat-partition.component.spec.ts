/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeatAllotmentTestModule } from '../../../test.module';
import { SeatPartitionComponent } from 'app/entities/seat-partition/seat-partition.component';
import { SeatPartitionService } from 'app/entities/seat-partition/seat-partition.service';
import { SeatPartition } from 'app/shared/model/seat-partition.model';

describe('Component Tests', () => {
  describe('SeatPartition Management Component', () => {
    let comp: SeatPartitionComponent;
    let fixture: ComponentFixture<SeatPartitionComponent>;
    let service: SeatPartitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [SeatPartitionComponent],
        providers: []
      })
        .overrideTemplate(SeatPartitionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SeatPartitionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SeatPartitionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SeatPartition('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.seatPartitions[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });
});
