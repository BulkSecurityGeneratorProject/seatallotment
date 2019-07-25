/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SeatAllotmentTestModule } from '../../../test.module';
import { SeatPartitionDeleteDialogComponent } from 'app/entities/seat-partition/seat-partition-delete-dialog.component';
import { SeatPartitionService } from 'app/entities/seat-partition/seat-partition.service';

describe('Component Tests', () => {
  describe('SeatPartition Management Delete Component', () => {
    let comp: SeatPartitionDeleteDialogComponent;
    let fixture: ComponentFixture<SeatPartitionDeleteDialogComponent>;
    let service: SeatPartitionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [SeatPartitionDeleteDialogComponent]
      })
        .overrideTemplate(SeatPartitionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SeatPartitionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SeatPartitionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('9fec3727-3421-4967-b213-ba36557ca194');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
