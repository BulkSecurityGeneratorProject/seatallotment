/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SeatAllotmentTestModule } from '../../../test.module';
import { ApplicationlogDeleteDialogComponent } from 'app/entities/applicationlog/applicationlog-delete-dialog.component';
import { ApplicationlogService } from 'app/entities/applicationlog/applicationlog.service';

describe('Component Tests', () => {
  describe('Applicationlog Management Delete Component', () => {
    let comp: ApplicationlogDeleteDialogComponent;
    let fixture: ComponentFixture<ApplicationlogDeleteDialogComponent>;
    let service: ApplicationlogService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [ApplicationlogDeleteDialogComponent]
      })
        .overrideTemplate(ApplicationlogDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationlogDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationlogService);
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
