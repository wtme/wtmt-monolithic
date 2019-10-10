import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OvertimeService } from 'app/entities/overtime/overtime.service';
import { IOvertime, Overtime } from 'app/shared/model/overtime.model';
import { OvertimeStatus } from 'app/shared/model/enumerations/overtime-status.model';

describe('Service Tests', () => {
  describe('Overtime Service', () => {
    let injector: TestBed;
    let service: OvertimeService;
    let httpMock: HttpTestingController;
    let elemDefault: IOvertime;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OvertimeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Overtime(
        0,
        'AAAAAAA',
        'AAAAAAA',
        OvertimeStatus.OPEN,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Overtime', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Overtime(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Overtime', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            name: 'BBBBBB',
            status: 'BBBBBB',
            details: 'BBBBBB',
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            overtimeInHours: 1,
            totalBillableHours: 1,
            totalCostingAmount: 1,
            note: 'BBBBBB',
            location: 'BBBBBB',
            employeeName: 'BBBBBB',
            employeeEmail: 'BBBBBB',
            departmentName: 'BBBBBB',
            departmentCode: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Overtime', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            name: 'BBBBBB',
            status: 'BBBBBB',
            details: 'BBBBBB',
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            overtimeInHours: 1,
            totalBillableHours: 1,
            totalCostingAmount: 1,
            note: 'BBBBBB',
            location: 'BBBBBB',
            employeeName: 'BBBBBB',
            employeeEmail: 'BBBBBB',
            departmentName: 'BBBBBB',
            departmentCode: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Overtime', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
