//$(function() {
////    $( "#customer-id" ).autocomplete({
////        source: function( request, response ) {
////            $.ajax({
////                method: "post",
////                url: "/OrderFlowers/contact/getPerson",
////                dataType: "json",
////                data: {term: request.term},
////                success: function(data) {
////                    response($.map(data, function(item) {
////                        var returnValue = item.surname + " " + item.name;
////                        if (item.patronymic !== null)
////                            returnValue += " " + item.patronymic;
////                        return {
////                            value: returnValue,
////                            id: item.id
////                        };
////                    }));
////                }
////            });
////        },
////        minLength: 2,
////        select: function( event, ui ) {
////            $( "#customer-hidden-id" ).val( ui.item.id);
////        }
////    });
////    $( "#recipient-id" ).autocomplete({
////        source: function( request, response ) {
////            $.ajax({
////                method: "post",
////                url: "/OrderFlowers/contact/getPerson",
////                dataType: "json",
////                data: {term: request.term},
////                success: function(data) {
////                    response($.map(data, function(item) {
////                        var returnValue = item.surname + " " + item.name;
////                        if (item.patronymic !== null)
////                            returnValue += " " + item.patronymic;
////                        return {
////                            value: returnValue,
////                            id: item.id
////                        };
////                    }));
////                }
////            });
////        },
////        minLength: 2,
////        select: function( event, ui ) {
////            $( "#recipient-hidden-id" ).val( ui.item.id);
////        }
////    });
//
//    $("#loginForm").validate({
//
//        // Specify the validation rules
//        rules: {
//            j_username: {
//                required: true,
//                minlength: 5
//            },
//            j_password: {
//                required: true,
//                minlength: 6
//            }
//        },
//
//        // Specify the validation error messages
//        messages: {
//            j_username: {
//                required:"Введите свой логин",
//                minlength: "Логин должно содержать не менее 5 символов"
//            },
//            j_password: {
//                required:"Введите свой пароль",
//                minlength: "Пароль должно содержать не менее 6 символов"
//            }
//        }
//
////        submitHandler: function(form) {
////            form.submit();
////        }
//    });
//
//});


