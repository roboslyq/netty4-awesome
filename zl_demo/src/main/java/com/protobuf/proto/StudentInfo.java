// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Studuent.proto

package com.protobuf.proto;

public final class StudentInfo {
  private StudentInfo() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_protobuf_proto_MyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_protobuf_proto_MyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_protobuf_proto_MyResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_protobuf_proto_MyResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_protobuf_proto_MyAge_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_protobuf_proto_MyAge_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_protobuf_proto_MyStudent_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_protobuf_proto_MyStudent_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\016Studuent.proto\022\022com.protobuf.proto\"\035\n\t" +
      "MyRequest\022\020\n\010username\030\001 \001(\t\"\036\n\nMyRespons" +
      "e\022\020\n\010realName\030\001 \001(\t\"\024\n\005MyAge\022\013\n\003age\030\001 \001(" +
      "\005\"4\n\tMyStudent\022\014\n\004name\030\001 \001(\t\022\013\n\003age\030\002 \001(" +
      "\005\022\014\n\004city\030\003 \001(\t2\273\001\n\016StudentService\022X\n\025Ge" +
      "tRealNameByUserName\022\035.com.protobuf.proto" +
      ".MyRequest\032\036.com.protobuf.proto.MyRespon" +
      "se\"\000\022O\n\017GetStudentByAge\022\031.com.protobuf.p" +
      "roto.MyAge\032\035.com.protobuf.proto.MyStuden" +
      "t\"\0000\001B#\n\022com.protobuf.protoB\013StudentInfo" +
      "P\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_protobuf_proto_MyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_protobuf_proto_MyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_protobuf_proto_MyRequest_descriptor,
        new String[] { "Username", });
    internal_static_com_protobuf_proto_MyResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_protobuf_proto_MyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_protobuf_proto_MyResponse_descriptor,
        new String[] { "RealName", });
    internal_static_com_protobuf_proto_MyAge_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_protobuf_proto_MyAge_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_protobuf_proto_MyAge_descriptor,
        new String[] { "Age", });
    internal_static_com_protobuf_proto_MyStudent_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_protobuf_proto_MyStudent_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_protobuf_proto_MyStudent_descriptor,
        new String[] { "Name", "Age", "City", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
