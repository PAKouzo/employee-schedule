import { TranslationMessages } from 'react-admin';

const customVietnameseMessages: TranslationMessages = {
    ra: {
        action: {
          add_filter: 'Thêm bộ lọc',
          add: 'Thêm',
          back: 'Quay lại',
          bulk_actions: '%{smart_count} đã chọn',
          cancel: 'Hủy bỏ',
          clear_array_input: 'Xóa danh sách',
          clear_input_value: 'Xóa giá trị đầu vào',
          clone: 'Sao chép',
          confirm: 'Xác nhận',
          create: 'Tạo',
          create_item: 'Tạo %{item}',
          delete: 'Xóa',
          edit: 'Chỉnh sửa',
          export: 'Xuất',
          list: 'Danh sách',
          refresh: 'Làm mới',
          remove_filter: 'Xóa bộ lọc này',
          remove_all_filters: 'Xóa tất cả bộ lọc',
          remove: 'Xóa',
          save: 'Lưu',
          search: 'Tìm kiếm',
          select_all: 'Chọn tất cả',
          select_row: 'Chọn hàng này',
          show: 'Hiển thị',
          sort: 'Sắp xếp',
          undo: 'Hoàn tác',
          unselect: 'Bỏ chọn',
          expand: 'Mở rộng',
          close: 'Đóng',
          open_menu: 'Mở menu',
          close_menu: 'Đóng menu',
          update: 'Cập nhật',
          move_up: 'Di chuyển lên',
          move_down: 'Di chuyển xuống',
          open: 'Mở',
          toggle_theme: 'Chuyển đổi chủ đề',
          select_columns: 'Chọn cột',
          update_application: 'Tải lại ứng dụng',
        },
        boolean: {
          true: 'Có',
          false: 'Không',
          null: ' ',
        },
        page: {
          create: 'Thêm %{name}',
          dashboard: 'Bảng điều khiển',
          edit: '%{name} %{id}',
          error: 'Có lỗi xảy ra',
          list: '%{name}',
          loading: 'Đang tải',
          not_found: 'Không tìm thấy',
          show: '%{name} %{id}',
          empty: 'Chưa có %{name}.',
          invite: 'Bạn có muốn thêm mới không?'
        },
        input: {
          file: {
            upload_several: 'Kéo thả file vào đây hoặc nhấn để chọn.',
            upload_single: 'Kéo thả file vào đây hoặc nhấn để chọn.'
          },
          image: {
            upload_several: 'Kéo thả hình ảnh vào đây hoặc nhấn để chọn.',
            upload_single: 'Kéo thả hình ảnh vào đây hoặc nhấn để chọn.'
          },
          references: {
            all_missing: 'Không tìm thấy tài liệu tham chiếu liên quan.',
            many_missing: 'Ít nhất một trong những tài liệu tham chiếu liên quan không còn tồn tại.',
            single_missing: 'Tài liệu tham chiếu liên quan không còn tồn tại.'
          },
          password: {
            toggle_visible: 'Ẩn mật khẩu',
            toggle_hidden: 'Hiển thị mật khẩu',
          },
        },
        message: {
          about: 'Về',
          are_you_sure: 'Bạn có chắc chắn không?',
          auth_error: 'Có lỗi xảy ra khi xác thực token.',
          bulk_delete_content: 'Bạn có chắc chắn muốn xóa %{name} này? |||| Bạn có chắc chắn muốn xóa %{smart_count} mục này?',
          bulk_delete_title: 'Xóa %{name} |||| Xóa %{smart_count} %{name} mục',
          bulk_update_content: 'Bạn có chắc chắn muốn cập nhật %{name} này? |||| Bạn có chắc chắn muốn cập nhật %{smart_count} mục này?',
          bulk_update_title: 'Cập nhật %{name} |||| Cập nhật %{smart_count} %{name}',
          clear_array_input: 'Bạn có chắc chắn muốn xóa toàn bộ danh sách?',
          delete_content: 'Bạn có chắc chắn muốn xóa mục này?',
          delete_title: 'Xóa %{name} #%{id}',
          details: 'Chi tiết',
          error: 'Lỗi cục bộ xảy ra và yêu cầu của bạn không thể hoàn thành.',
          invalid_form: 'Biểu mẫu không hợp lệ. Vui lòng kiểm tra các lỗi.',
          loading: 'Trang đang tải, vui lòng đợi một chút',
          no: 'Không',
          not_found: 'URL không chính xác hoặc bạn đã nhấp vào liên kết sai',
          yes: 'Có',
          unsaved_changes: 'Một số thay đổi chưa được lưu. Bạn có chắc chắn muốn bỏ qua?',
        },
        navigation: {
          no_results: 'Không tìm thấy kết quả',
          no_more_results: 'Trang số %{page} vượt quá phạm vi. Thử trang trước.',
          page_out_of_boundaries: 'Số trang %{page} ngoài giới hạn',
          page_out_from_end: 'Kết thúc phân trang',
          page_out_from_begin: 'Số trang phải lớn hơn 1',
          page_range_info: '%{offsetBegin}-%{offsetEnd} của %{total}',
          partial_page_range_info: '%{offsetBegin}-%{offsetEnd} trong tổng số nhiều hơn %{offsetEnd}',
          current_page: 'Trang %{page}',
          page: 'Đi đến trang %{page}',
          first: 'Đi đến trang đầu tiên',
          last: 'Đi đến trang cuối cùng',
          page_rows_per_page: 'Hàng mỗi trang',
          next: 'Tiếp theo',
          previous: 'Trước đó',
          skip_nav: 'Đi đến nội dung',
        },
        sort: {
          sort_by: 'Sắp xếp theo %{field} %{order}',
          ASC: 'tăng dần',
          DESC: 'giảm dần',
        },
        auth: {
          auth_check_error: 'Bạn cần đăng nhập để tiếp tục',
          user_menu: 'Hồ sơ',
          username: 'Tên đăng nhập',
          password: 'Mật khẩu',
          sign_in: 'Đăng nhập',
          sign_in_error: 'Xác thực thất bại, vui lòng thử lại.',
          logout: 'Đăng xuất'
        },
        notification: {
          updated: 'Bản ghi đã được cập nhật |||| %{smart_count} bản ghi đã được cập nhật',
          created: 'Bản ghi đã được tạo',
          deleted: 'Bản ghi đã được xóa |||| %{smart_count} bản ghi đã được xóa',
          bad_item: 'Bản ghi không hợp lệ',
          item_doesnt_exist: 'Bản ghi không tồn tại',
          http_error: 'Lỗi giao tiếp với máy chủ',
          data_provider_error: 'Lỗi nhà cung cấp dữ liệu. Kiểm tra bảng điều khiển để biết chi tiết.',
          i18n_error: 'Không tìm thấy bản dịch cho ngôn ngữ đã chọn',
          canceled: 'Hành động đã bị hủy',
          logged_out: 'Phiên làm việc đã kết thúc, vui lòng đăng nhập lại.',
          not_authorized: "Bạn không có quyền truy cập tài nguyên này.",
          application_update_available: 'Một phiên bản mới đã sẵn sàng.',
        },
        validation: {
          required: 'Bắt buộc',
          minLength: 'Phải dài ít nhất %{min} ký tự',
          maxLength: 'Phải dài không quá %{max} ký tự',
          minValue: 'Phải ít nhất là %{min}',
          maxValue: 'Phải tối đa là %{max}',
          number: 'Phải là số',
          email: 'Phải là địa chỉ email hợp lệ',
          oneOf: 'Phải là một trong số: %{options}',
          regex: 'Phải khớp với định dạng (biểu thức chính quy): %{pattern}'
        },
        saved_queries: {
            label: 'Truy vấn đã lưu',
            query_name: 'Tên truy vấn',
            new_label: 'Lưu truy vấn hiện tại...',
            new_dialog_title: 'Lưu truy vấn hiện tại thành',
            remove_label: 'Xóa truy vấn đã lưu',
            remove_label_with_name: 'Xóa truy vấn "%{name}"',
            remove_dialog_title: 'Xóa truy vấn đã lưu?',
            remove_message: 'Bạn có chắc chắn muốn xóa mục này khỏi danh sách các truy vấn đã lưu?',
            help: 'Lọc danh sách và lưu truy vấn này để sử dụng sau',
        },
        configurable: {
            customize: 'Tùy chỉnh',
            configureMode: 'Cấu hình trang này',
            inspector: {
                title: 'Thanh tra',
                content: 'Di chuột qua các phần tử UI để cấu hình chúng',
                reset: 'Đặt lại Cài đặt',
                hideAll: 'Ẩn Tất cả',
                showAll: 'Hiển thị Tất cả',
            },
            Datagrid: {
                title: 'Bảng dữ liệu',
                unlabeled: 'Cột không gắn nhãn #%{column}',
            },
            SimpleForm: {
                title: 'Biểu mẫu',
                unlabeled: 'Đầu vào không gắn nhãn #%{input}',
            },
            SimpleList: {
                title: 'Danh sách',
                primaryText: 'Văn bản chính',
                secondaryText: 'Văn bản phụ',
                tertiaryText: 'Văn bản cấp ba',
            },
        },
    },
    resources: {
        employee: {
            name: 'Nhân viên |||| Nhân viên',
            fields: {
                number: 'Số',
                active: 'Hoạt động?',
                color: 'Màu',
                name: 'Tên',
                surname: 'Họ',
                phone: 'Số điện thoại',
                email: 'Địa chỉ email',
            },
        },
        customer: {
            name: 'Khách hàng |||| Khách hàng',
            fields: {
                vat: 'Mã số thuế',
                name: 'Tên',
                email: 'Địa chỉ email',
                phone: 'Số điện thoại',
                zipcode: 'Mã bưu điện',
                city: 'Thành phố',
                website: 'Trang web',
            }
        }
    },
    ess: {
      calendar: {
        name: "Lịch",
        event: {
          error: "Đã xảy ra lỗi.",
          error_create: "Lỗi khi tạo sự kiện",
          success_create: "Sự kiện đã được tạo thành công",
          error_update: "Lỗi khi cập nhật sự kiện",
          success_update: "Sự kiện đã được cập nhật thành công",
          error_delete: "Lỗi khi xóa sự kiện",
          success_delete: "Sự kiện đã được xóa thành công",
          recurring: {
            thisev: "Sự kiện này",
            thisandfoll: "Sự kiện này và các sự kiện tiếp theo"
          }
        },
        calendarlist: {
          number: "Số hiệu"
        },
      },
      users: {
        password_update: {
          btn_label: "Thay đổi Mật khẩu",
          updated: "Mật khẩu đã được thay đổi thành công!",
          updated_error: "Có lỗi xảy ra khi cập nhật mật khẩu.",
          field_label: "Mật khẩu mới"
        }
      },
      summary: {
        name: "Báo cáo"
      }
    }
};

export default customVietnameseMessages;
